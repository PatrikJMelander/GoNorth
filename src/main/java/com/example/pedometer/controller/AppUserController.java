package com.example.pedometer.controller;

import com.example.pedometer.DTO.AppUserResponse;
import com.example.pedometer.DTO.TeamResponse;
import com.example.pedometer.model.AppUser;
import com.example.pedometer.model.Team;
import com.example.pedometer.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@CrossOrigin
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> getAllAppUsers() {
        return ResponseEntity.ok(appUserService.getAll());
    }

    @GetMapping("/validate/login")
    public ResponseEntity<AppUserResponse> validateLogin(@RequestParam String email,
                                                @RequestParam String password) {
        return ResponseEntity.ok(appUserService.validateLogin(email, password));
    }

    @GetMapping("/get/one")
    public ResponseEntity<AppUserResponse> getOneByEmail(@RequestParam  String email) {
        return ResponseEntity.ok(appUserService.getAppUserByEmail(email));
    }

    @PostMapping("/add")
    public ResponseEntity<AppUserResponse> addAppUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.addAppUser(appUser));
    }

    @GetMapping("/update/password")
    public ResponseEntity<AppUserResponse> updatePassword(@RequestParam String password,
                                                          @RequestParam String email) {
        return ResponseEntity.ok(appUserService.updatePassword(password,email));
    }

    @PostMapping("/update")
    public ResponseEntity<AppUserResponse> updateUser(@RequestBody AppUser appUser) {
        return ResponseEntity.ok(appUserService.updateUser(appUser));
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deleteAppUser(@RequestParam String email,
                                                @RequestParam String password) {
        return ResponseEntity.ok(appUserService.deleteAppUser(email, password));
    }

    @GetMapping("/delete/bypostman")
    public ResponseEntity<String> deleteAppUserByPostman(@RequestParam String email) {
        return ResponseEntity.ok(appUserService.deleteAppUserByPostman(email));
    }

    @GetMapping("/get/all/steps")
    public ResponseEntity<Integer> getAllSteps(@RequestParam String email){
        return ResponseEntity.ok(appUserService.getStepsOfAppUser(email));
    }

    @PostMapping("/add/steps")
    public ResponseEntity<AppUserResponse> addStepsToAppUser(@RequestBody AppUser appUser,
                                                             @RequestParam int steps,
                                                             @RequestParam
                                                                 @Nullable
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appUserService.addStepsToUser(appUser, steps, date));
    }

    @PostMapping("/add/personalGoal")
    public ResponseEntity<AppUserResponse> addStepsToAppUser(@RequestParam String email,
                                                             @RequestParam int personalGoal) {
        return ResponseEntity.ok(appUserService.addPersonalGoalToUser(email, personalGoal));
    }

    @GetMapping("/getUserTeams")
    public ResponseEntity<List<TeamResponse>> getAllTeams(@RequestParam String email){

        List<Team> allTeams = appUserService.getAllTeams(email);
        return ResponseEntity.ok(allTeams.stream().map(Team::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/leave/team")
    public ResponseEntity<String> leaveTeam(@RequestParam String email, @RequestParam String teamName){
        return ResponseEntity.ok(appUserService.removeFromTeam(email, teamName));
    }

}
