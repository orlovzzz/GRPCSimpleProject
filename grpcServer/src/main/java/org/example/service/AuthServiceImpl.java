package org.example.service;

import com.example.grpc.AuthServiceGrpc;
import com.example.grpc.AuthServiceOuterClass;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.ClientRepository;
import org.example.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class AuthServiceImpl extends AuthServiceGrpc.AuthServiceImplBase {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void greeting(AuthServiceOuterClass.LoginRequest request, StreamObserver<AuthServiceOuterClass.LoginResponse> responseObserver) {
        Client client = clientRepository.findByLogin(request.getLogin());
        AuthServiceOuterClass.LoginResponse response;
        if (client != null) {
            if (client.getPassword().equals(request.getPassword())) {
                response = AuthServiceOuterClass.LoginResponse
                        .newBuilder().setGreeting("Hello, " + request.getLogin()).build();
            } else {
                response = AuthServiceOuterClass.LoginResponse
                        .newBuilder().setGreeting("Incorrect password").build();
            }
        } else {
            response = AuthServiceOuterClass.LoginResponse
                    .newBuilder().setGreeting("Incorrect login").build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void registration(AuthServiceOuterClass.RegRequest request, StreamObserver<AuthServiceOuterClass.RegResponse> responseObserver) {
        Client client = new Client(request.getLogin(), request.getPassword());
        AuthServiceOuterClass.RegResponse response;
        if (clientRepository.findByLogin(client.getLogin()) == null) {
            clientRepository.save(client);
            response = AuthServiceOuterClass.RegResponse
                    .newBuilder().setSuccess("Created new client").build();
        } else {
            response = AuthServiceOuterClass.RegResponse
                    .newBuilder().setSuccess("Login already in use").build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}