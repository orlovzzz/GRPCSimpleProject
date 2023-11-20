package org.example.controller;

import com.example.grpc.AuthServiceGrpc;
import com.example.grpc.AuthServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.DTO.ClientDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody ClientDTO client) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
        AuthServiceGrpc.AuthServiceBlockingStub stub = AuthServiceGrpc.newBlockingStub(channel);
        AuthServiceOuterClass.LoginRequest request = AuthServiceOuterClass.LoginRequest
                .newBuilder().setLogin(client.getLogin()).setPassword(client.getPassword()).build();
        AuthServiceOuterClass.LoginResponse response = stub.greeting(request);
        return response.getGreeting();
    }

    @PostMapping("/reg")
    public String reg(@RequestBody ClientDTO client) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:9090").usePlaintext().build();
        AuthServiceGrpc.AuthServiceBlockingStub stub = AuthServiceGrpc.newBlockingStub(channel);
        AuthServiceOuterClass.RegRequest request = AuthServiceOuterClass.RegRequest
                .newBuilder().setLogin(client.getLogin()).setPassword(client.getPassword()).build();
        AuthServiceOuterClass.RegResponse response = stub.registration(request);
        return response.getSuccess();
    }
}
