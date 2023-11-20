package org.example.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.service.AuthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

//@Configuration
//public class GrpcConfig {
//
//    @Bean
//    public void createServer() throws IOException, InterruptedException {
//        Server server = ServerBuilder.forPort(8082).addService(new AuthServiceImpl()).build();
//        server.start();
//        System.out.println("Server started");
//        server.awaitTermination();
//    }
//
//}
