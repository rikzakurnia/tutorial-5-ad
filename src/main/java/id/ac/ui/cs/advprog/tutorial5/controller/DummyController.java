package id.ac.ui.cs.advprog.tutorial5.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dummy")
public class DummyController {

    @GetMapping("/read")
    @PreAuthorize("hasAuthority('medicine:create')")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello I'm creating medicine");
    }

    @GetMapping("/buy")
    @PreAuthorize("hasAuthority('order:create')")
    public ResponseEntity<String> sayHelloBuy() {
        return ResponseEntity.ok("Hello I'm buying medicine");
    }
}
