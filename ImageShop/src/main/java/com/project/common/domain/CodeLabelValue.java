package com.project.common.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//클래스의 final 필드와 @NonNull 필드에 대한 생성자를 자동으로 생성
// requiredArgsConstructor을 사용하지 않을시
// 생성자를 수동으로 작성
/*public CodeLabelValue(String value, String label) {
    this.value = value;
    this.label = label;
    }*/

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class CodeLabelValue {
	private final String value;
	private final String label;
}
