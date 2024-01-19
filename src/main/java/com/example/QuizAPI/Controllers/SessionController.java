package com.example.QuizAPI.Controllers;

import com.example.QuizAPI.DatabaseMapping.Session;
import com.example.QuizAPI.DatabaseMapping.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;


@RestController
@RequestMapping("/sessions")
public class SessionController {
    private final SessionRepository repository;
    private static final int CODE_LENGTH = 5;
    @Autowired
    private QuestionController questionController;

    SessionController(SessionRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/new/{c_id}")
    public ResponseEntity<Session> postSession(@PathVariable String c_id, UriComponentsBuilder ucb) {
        Session session = new Session(
                null,
                getNextCode(getHighestKey()),
                new Date(),
                c_id,
                questionController.getRandomOf(CODE_LENGTH, c_id)
        );
        Session savedSession = repository.save(session);

        URI locationOfNewSession = ucb
                .path("sessions/key/{key}")
                .buildAndExpand(savedSession.key())
                .toUri();

        return ResponseEntity.created(locationOfNewSession).build();
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<Session> getByKey(@PathVariable String key) {
        Session session = repository.findByKey(key);
        if (session != null) return ResponseEntity.ok(session);
        return ResponseEntity.notFound().build();
    }

    private String getHighestKey() {
        Session session = repository.findFirstByOrderByKeyDesc();

        if (session == null) return "A".repeat(CODE_LENGTH - 1) + '@';
        return session.key();
    }
    private String getNextCode(String old) {
        char[] output = old.toCharArray();
        boolean carry = false;
        int resetVal = 'Z' - 'A' + 1; // from Z to A

        output[CODE_LENGTH - 1]++;

        for (int i = CODE_LENGTH - 1; i >= 0; i--) {
            if (carry) output[i]++;
            carry = false;

            if (output[i] > 'Z') {
                carry = true;
                output[i] -= resetVal;
            }
        }

        return new String(output);
    }

}