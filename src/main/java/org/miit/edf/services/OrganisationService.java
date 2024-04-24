package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.models.Organisation;
import org.miit.edf.repos.OrganisationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganisationService {
    private final OrganisationRepo organisationRepo;

    public Organisation createOrganisation(Organisation organisation) {
        return organisationRepo.save(organisation);
    }

    public Organisation findById(Long id) {
        return organisationRepo.findById(id).orElse(null);
    }

    public List<Organisation> findAll() {
        return organisationRepo.findAll();
    }

    public void deleteById(Long id) {
        organisationRepo.deleteById(id);
    }
}
