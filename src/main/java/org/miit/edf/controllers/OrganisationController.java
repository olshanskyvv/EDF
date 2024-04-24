package org.miit.edf.controllers;

import lombok.RequiredArgsConstructor;
import org.miit.edf.models.Organisation;
import org.miit.edf.services.OrganisationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organisations")
public class OrganisationController {
    private final OrganisationService organisationService;

    @GetMapping()
    public ResponseEntity<List<Organisation>> findAll() {
        List<Organisation> organisations = organisationService.findAll();
        if (organisations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organisations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organisation> findById(@PathVariable Long id) {
        Organisation organisation = organisationService.findById(id);
        if (organisation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(organisation);
    }

    @PostMapping("/create")
    public ResponseEntity<Organisation> create(@RequestBody Organisation organisation) {
        return ResponseEntity.ok(organisationService.createOrganisation(organisation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        organisationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
