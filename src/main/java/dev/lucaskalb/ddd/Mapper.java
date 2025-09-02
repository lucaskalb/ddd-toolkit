package dev.lucaskalb.ddd;

public interface Mapper<S, T> {

  T map(Context context, S source);
}