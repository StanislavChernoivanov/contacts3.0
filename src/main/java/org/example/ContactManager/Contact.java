package org.example.ContactManager;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Contact {

    private final String fullName;

    private final String phone;

    private final String email;

}
