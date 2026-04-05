package com.email.writer;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/email")
@AllArgsConstructor
class Emailcontroller {

    private final emailservice emailservice;

    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateEmail(@RequestBody emailrequest emailrequest) {
        Map<String, Object> response = emailservice.generateEmail(emailrequest);
        return ResponseEntity.ok(response);
    }
}