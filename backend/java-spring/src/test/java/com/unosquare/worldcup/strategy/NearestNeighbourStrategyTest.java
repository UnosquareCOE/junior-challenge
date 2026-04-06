package com.unosquare.worldcup.strategy;

import com.unosquare.worldcup.dto.MatchWithCityDTO;
import com.unosquare.worldcup.dto.OptimisedRouteDTO;
import com.unosquare.worldcup.model.City;
import com.unosquare.worldcup.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NearestNeighbourStrategyTest — YOUR TASK #4
 *
 * ============================================================
 * WHAT YOU NEED TO IMPLEMENT:
 * ============================================================
 *
 * Write unit tests for the NearestNeighbourStrategy.
 * Each test has a TODO comment explaining what to test.
 *
 *
 */
class NearestNeighbourStrategyTest {

    private NearestNeighbourStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new NearestNeighbourStrategy();
    }

    @Test
    void shouldReturnValidRouteForMultipleMatches() {
        // Arrange: Create a list of matches across different cities and dates
        City cityUSA = new City("city-1", "New York", "USA", 40.7128, -74.0060, "Stadium 1", 200.0);
        City cityMexico = new City("city-2", "Mexico City", "Mexico", 19.4326, -99.1332, "Stadium 2", 150.0);
        City cityCanada = new City("city-3", "Toronto", "Canada", 43.6532, -79.3832, "Stadium 3", 180.0);

        Team team1 = new Team("team-1", "Team A", "TMA", "A");
        Team team2 = new Team("team-2", "Team B", "TMB", "A");

        MatchWithCityDTO match1 = new MatchWithCityDTO("match-1", team1, team2, cityUSA,
                LocalDateTime.of(2026, 6, 14, 18, 0), "A", 1, 150.0);
        MatchWithCityDTO match2 = new MatchWithCityDTO("match-2", team1, team2, cityMexico,
                LocalDateTime.of(2026, 6, 15, 18, 0), "A", 2, 150.0);
        MatchWithCityDTO match3 = new MatchWithCityDTO("match-3", team1, team2, cityCanada,
                LocalDateTime.of(2026, 6, 16, 18, 0), "A", 3, 150.0);

        List<MatchWithCityDTO> matches = List.of(match1, match2, match3);

        // Act: Call strategy.optimise(matches)
        OptimisedRouteDTO result = strategy.optimise(matches, null);

        // Assert: Verify the result has stops, totalDistance > 0, and strategy = "nearest-neighbour"
        assertNotNull(result);
        assertEquals(3, result.getStops().size());
        assertTrue(result.getTotalDistance() > 0);
        assertEquals("nearest-neighbour", result.getStrategy());
    }

    @Test
    void shouldReturnEmptyRouteForEmptyMatches() {
        // Arrange: Create an empty list of matches
        List<MatchWithCityDTO> matches = Collections.emptyList();

        // Act: Call strategy.optimise(Collections.emptyList())
        OptimisedRouteDTO result = strategy.optimise(matches, null);

        // Assert: Verify the result has empty stops and totalDistance = 0
        assertNotNull(result);
        assertTrue(result.getStops().isEmpty());
        assertEquals(0, result.getTotalDistance());
        assertFalse(result.isFeasible());
    }

    @Test
    void shouldReturnZeroDistanceForSingleMatch() {
        // Arrange: Create a list with a single match
        City city = new City("city-1", "New York", "USA", 40.7128, -74.0060, "Stadium", 200.0);
        Team team1 = new Team("team-1", "Team A", "TMA", "A");
        Team team2 = new Team("team-2", "Team B", "TMB", "A");

        MatchWithCityDTO match = new MatchWithCityDTO("match-1", team1, team2, city,
                LocalDateTime.of(2026, 6, 14, 18, 0), "A", 1, 150.0);

        List<MatchWithCityDTO> matches = List.of(match);

        // Act: Call strategy.optimise(matches)
        OptimisedRouteDTO result = strategy.optimise(matches, null);

        // Assert: Verify totalDistance = 0 and stops.size() = 1
        assertNotNull(result);
        assertEquals(1, result.getStops().size());
        assertEquals(0, result.getTotalDistance());
    }

}
