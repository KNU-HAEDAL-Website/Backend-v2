package com.haedal.haedalweb.domain.user.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum JoinSemester {
	SEMESTER_2024_1("2024-1"),
	SEMESTER_2024_2("2024-2"),
	SEMESTER_2025_1("2025-1"),
	SEMESTER_2025_2("2025-2"),
	SEMESTER_2026_1("2026-1"),
	SEMESTER_2026_2("2026-2"),
	SEMESTER_2027_1("2027-1"),
	SEMESTER_2027_2("2027-2"),
	SEMESTER_2028_1("2028-1"),
	SEMESTER_2028_2("2028-2"),
	SEMESTER_2029_1("2029-1"),
	SEMESTER_2029_2("2029-2");

	private final String label;

	JoinSemester(String label) {
		this.label = label;
	}

	public static List<JoinSemester> getAvailableSemesters() {
		LocalDate now = LocalDate.now();
		int currentYear = now.getYear();
		int currentMonth = now.getMonthValue();
		String currentSemester = currentYear + "-" + (currentMonth <= 6 ? "1" : "2");

		return Arrays.stream(JoinSemester.values())
			.filter(semester -> semester.getLabel().compareTo(currentSemester) <= 0)
			.collect(Collectors.toList());
	}
}
