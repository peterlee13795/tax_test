package com.tax.test.api.auth;

import com.tax.test.core.exception.LogicException;
import com.tax.test.domain.auth.AllowedMember;
import com.tax.test.type.StatusType;
import com.tax.test.util.JsonUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SignupChecker {

	private final Set<String> allowedMembers;

	public SignupChecker(ResourceLoader resourceLoader) throws Exception {
		String json = readResource(resourceLoader, "classpath:/allowed-member.json");

		this.allowedMembers = JsonUtil.jsonToList(json, AllowedMember.class).stream()
					.map(allowedMember -> allowedMember.getName() + allowedMember.getRegNo())
					.collect(Collectors.toSet());


	}

	public String readResource(ResourceLoader resourceLoader, String location) throws Exception {
		Resource resource = resourceLoader.getResource(location);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			return content.toString();
		}
	}

	public void validateMember(String name, String regNo) {
		boolean allowed = this.allowedMembers.contains(name + regNo);
		if(!allowed) {
			throw new LogicException(StatusType.MEMBER_NOT_ALLOWED);
		}
	}

}
