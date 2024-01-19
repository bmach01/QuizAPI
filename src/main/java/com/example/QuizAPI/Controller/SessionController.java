package com.example.QuizAPI.Controller;

import com.example.QuizAPI.DAL.Session;
import com.example.QuizAPI.DAL.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
    @Autowired
    private QuestionController questionController;

    SessionController(SessionRepository repository) {
        this.repository = repository;
    }


    @PostMapping("/new/{c_id}")
    public ResponseEntity<Session> postSession(@PathVariable String c_id, UriComponentsBuilder ucb) {
        Session session = new Session(
                null,
                getNextCode(highestKey()),
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

    @GetMapping("/highestKey")
    private String highestKey() {
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