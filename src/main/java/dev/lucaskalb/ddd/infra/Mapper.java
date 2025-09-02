package dev.lucaskalb.ddd.infra;

public interface Mapper<S, T> {

  T map(Context context, S source);
}
