package com.anxocode.jinn;

import java.util.Comparator;

public abstract class Comparison {

	protected static final Comparison ACTIVE = new Comparison() {
		@Override
		public <T extends Comparable<T>> Comparison compare(T left, T right) {
			final int result = left.compareTo(right);
			
			return result < 0 ? LESS : (result > 0 ? GREATER : ACTIVE);
		}

		@Override
		public <T> Comparison compare(T left, T right,
				Comparator<? super T> comparator) {
			final int result = comparator.compare(left, right);
			
			return result < 0 ? LESS : (result > 0 ? GREATER : ACTIVE);
		}

		@Override
		public Comparison compare(int left, int right) {
			return left < right ? LESS : (left > right ? GREATER : ACTIVE);
		}

		@Override
		public Comparison compare(long left, long right) {
			return left < right ? LESS : (left > right ? GREATER : ACTIVE);
		}

		@Override
		public Comparison compare(float left, float right) {
			return left < right ? LESS : (left > right ? GREATER : ACTIVE);
		}

		@Override
		public Comparison compare(double left, double right) {
			return left < right ? LESS : (left > right ? GREATER : ACTIVE);
		}

		@Override
		public Comparison compare(boolean left, boolean right) {
			return left == right ? ACTIVE : (left ? GREATER : LESS);
		}

		@Override
		public int result() {
			return 0;
		}
	};

	private static final Comparison LESS = new InactiveComparisonChain(-1);

	private static final Comparison GREATER = new InactiveComparisonChain(1);

	private static final class InactiveComparisonChain extends Comparison {
		final int result;

		InactiveComparisonChain(int result) {
			this.result = result;
		}

		@Override
		public <T extends Comparable<T>> Comparison compare(T left, T right) {
			return this;
		}

		@Override
		public <T> Comparison compare(T left, T right,
				Comparator<? super T> comparator) {
			return this;
		}

		@Override
		public Comparison compare(int left, int right) {
			return this;
		}

		@Override
		public Comparison compare(long left, long right) {
			return this;
		}

		@Override
		public Comparison compare(float left, float right) {
			return this;
		}

		@Override
		public Comparison compare(double left, double right) {
			return this;
		}

		@Override
		public Comparison compare(boolean left, boolean right) {
			return this;
		}

		@Override
		public int result() {
			return result;
		}
	}
	
	private Comparison() {
	}

	public abstract <T extends Comparable<T>> Comparison compare(T left, T right);

	public abstract <T> Comparison compare(T left, T right,
			Comparator<? super T> comparator);

	public abstract Comparison compare(int left, int right);

	public abstract Comparison compare(long left, long right);

	public abstract Comparison compare(float left, float right);

	public abstract Comparison compare(double left, double right);

	public abstract Comparison compare(boolean left, boolean right);

	public abstract int result();
}