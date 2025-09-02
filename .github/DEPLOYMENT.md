# Deployment Configuration Guide

This document provides detailed instructions for configuring GitHub Actions to deploy the DDD Toolkit to Maven Central.

## Required GitHub Secrets

Navigate to your repository settings → Secrets and variables → Actions, and add the following secrets:

### 1. OSSRH_USERNAME
Your Sonatype OSSRH (Open Source Software Repository Hosting) username.
- **How to get**: Create an account at [issues.sonatype.org](https://issues.sonatype.org)
- **Format**: Your JIRA username (e.g., `johnsmith`)

### 2. OSSRH_TOKEN
Your Sonatype OSSRH token (not password).
- **How to get**: 
  1. Go to [s01.oss.sonatype.org](https://s01.oss.sonatype.org)
  2. Log in with your OSSRH credentials
  3. Click your username → Profile → User Token → Access User Token
- **Format**: Token string (e.g., `AbCdEf123456`)

### 3. GPG_PRIVATE_KEY
Your GPG private key for signing artifacts.
- **How to get**:
  ```bash
  # Generate a new GPG key (if you don't have one)
  gpg --gen-key
  
  # Export your private key
  gpg --armor --export-secret-keys YOUR_KEY_ID
  ```
- **Format**: Complete private key block including headers:
  ```
  -----BEGIN PGP PRIVATE KEY BLOCK-----
  [key content]
  -----END PGP PRIVATE KEY BLOCK-----
  ```

### 4. GPG_PASSPHRASE
The passphrase for your GPG private key.
- **Format**: Your GPG key passphrase as plain text

## GPG Key Setup

### Generate GPG Key
```bash
# Generate a new GPG key
gpg --gen-key

# Follow the prompts:
# - Real name: Your Name
# - Email: your-email@example.com (should match your GitHub email)
# - Choose a secure passphrase
```

### Export and Upload Public Key
```bash
# List your keys to get the key ID
gpg --list-secret-keys --keyid-format LONG

# Export public key to key servers
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
gpg --keyserver keys.openpgp.org --send-keys YOUR_KEY_ID
```

### Export Private Key for GitHub Secret
```bash
# Export private key (copy the entire output including headers)
gpg --armor --export-secret-keys YOUR_KEY_ID
```

## Sonatype OSSRH Setup

### 1. Create OSSRH Account
1. Go to [issues.sonatype.org](https://issues.sonatype.org)
2. Create an account
3. Create a new project ticket (New Project)

### 2. Request Access for GroupId
Create a ticket with:
- **Summary**: Request for new GroupId: `dev.lucaskalb`
- **Group Id**: `dev.lucaskalb`
- **Project URL**: `https://github.com/lucaskalb/ddd-toolkit`
- **SCM URL**: `https://github.com/lucaskalb/ddd-toolkit.git`

### 3. Verify Domain Ownership
If using a custom domain like `dev.lucaskalb`, you'll need to prove ownership:
- Option 1: Create a GitHub repository named `OSSRH-XXXXX` (ticket number)
- Option 2: Add TXT DNS record with ticket number

## Workflow Triggers

### Automatic Release (Tag Push)
```bash
# Create and push a version tag
git tag v1.0.0
git push origin v1.0.0
```

### Manual Release (GitHub UI)
1. Go to repository → Actions → Manual Release
2. Click "Run workflow"
3. Enter the version number (e.g., `1.0.0`)
4. Optionally skip tests
5. Click "Run workflow"

### Snapshot Deployment
- Automatically triggered on push to `main`/`master` branch
- Only runs if commit message contains "SNAPSHOT"
- Can be manually triggered via workflow dispatch

## Verification

### After Setup
1. Push a commit with "SNAPSHOT" in the message to test snapshot deployment
2. Check [s01.oss.sonatype.org](https://s01.oss.sonatype.org) for staging repository
3. For releases, check [search.maven.org](https://search.maven.org) after ~10-15 minutes

### Troubleshooting

**Common Issues:**

1. **GPG Signing Fails**
   - Ensure GPG_PRIVATE_KEY includes headers/footers
   - Verify GPG_PASSPHRASE is correct
   - Check that public key is uploaded to key servers

2. **OSSRH Authentication Fails**
   - Use token, not password for OSSRH_TOKEN
   - Verify OSSRH_USERNAME is your JIRA username

3. **Staging Repository Not Found**
   - Check if groupId is approved in OSSRH ticket
   - Verify all required metadata is present (license, SCM, developers)

4. **Release Not Appearing in Maven Central**
   - Check if staging repository was successfully closed and released
   - It can take 10-15 minutes for artifacts to sync to search.maven.org

## Security Best Practices

1. **Never commit secrets** to the repository
2. **Use GitHub Secrets** for all sensitive data
3. **Rotate keys regularly** - update GPG keys and tokens periodically
4. **Limit permissions** - use tokens with minimal required permissions
5. **Monitor releases** - review all deployments to ensure they're intentional