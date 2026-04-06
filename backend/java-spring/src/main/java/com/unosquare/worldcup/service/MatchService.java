package com.unosquare.worldcup.service;

import com.unosquare.worldcup.dto.MatchWithCityDTO;
import com.unosquare.worldcup.model.Match;
import com.unosquare.worldcup.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MatchService — YOUR TASK #2.1
 *
 * This service handles business logic for match operations.
 */
@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    // ============================================================
    //  Get matches with optional filters
    // ============================================================
    //
    // TODO: Implement this method
    //
    // Parameters (both optional):
    //   city  → filter by city ID
    //   date  → filter by date (LocalDate)
    //
    // Hint: Use matchRepository.findAllOrderByKickoff() for all matches,
    // or matchRepository.findByCityId(city) to filter by city.
    //
    // Convert Match entities to MatchWithCityDTO using MatchWithCityDTO.fromEntity(match)
    //
    // ============================================================
    public List<MatchWithCityDTO> getMatches(String city, LocalDate date) {
        List<Match> matches;

        if (city != null) {
            matches = matchRepository.findByCityId(city);
        } else {
            matches = matchRepository.findAllOrderByKickoff();
        }

        return matches.stream()
                .filter(match -> date == null || match.getKickoff().toLocalDate().equals(date))
                .map(MatchWithCityDTO::fromEntity)
                .toList();
    }

    /**
     * Get a match by ID.
     */
    public Optional<MatchWithCityDTO> getMatchById(String id) {
        return matchRepository.findById(id)
                .map(MatchWithCityDTO::fromEntity);
    }
}
