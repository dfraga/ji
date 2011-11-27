package com.anxocode.ji;

import static com.anxocode.ji.Ji.*;

import java.util.Arrays;

public class Sample implements Comparable<Sample> {

	public Long id;
	public String name;
	private int[] numbers;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = notNull(id, "id");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = or(trim(name), "NoName");
	}
	
	public int[] getNumbers() {
		return numbers;
	}
	
	public void setNumbers(int[] numbers) {
		this.numbers = notEmpty(numbers, "numbers");
	}
	
	public void perform(int param1, int param2) {
		check(param1 == param2 * 2, "param1 {} debe ser el doble de param2 {}", param1, param2);
		
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Sample && Ji.equals(this.id, ((Sample) obj).id)
				&& Ji.equals(this.name, ((Sample) obj).name)
				&& Arrays.equals(this.numbers, ((Sample) obj).numbers);
	}

	@Override
	public int hashCode() {
		return Ji.hashCode(this.id, this.name, this.numbers);
	}

	@Override
	public String toString() {
		return toStringBuilder(this).add(this.id).add("name", this.name)
				.add("numbers", this.numbers).toString();
	}

	@Override
	public int compareTo(Sample o) {
		return comparasion().compare(this.name, o.name).compare(this.id, o.id).result();
	}
	
}
