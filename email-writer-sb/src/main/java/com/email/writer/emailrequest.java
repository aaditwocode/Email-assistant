package com.email.writer;

import lombok.Data;

@Data
public class emailrequest {
    private String emailcontent;
    private String tone;

    public char[] getOriginalemail() {
        return tone.toCharArray();
    }

    public char[] getTone() {
        return tone.toCharArray();

    }
}
