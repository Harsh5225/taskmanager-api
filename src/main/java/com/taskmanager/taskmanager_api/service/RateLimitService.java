package com.taskmanager.taskmanager_api.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    // IP->token bucket Thread safe
    private final Map<String,Bucket> loginBuckets=new ConcurrentHashMap<>();

    // bucket creation : 5 request per minute
    private Bucket createLoginBucket(String ip){
        System.out.println("Creating new bucket for IP: " + ip);
        Bandwidth limit=Bandwidth.builder()
                .capacity(5)  // max tokens
                .refillGreedy(5, Duration.ofMinutes(1)) // refill 5 tokens every 1 minute
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public boolean allowLoginRequests(String ip){
        Bucket bucket=loginBuckets.computeIfAbsent(ip,this::createLoginBucket);
        return bucket.tryConsume(1);
    }

}

// AIM:
//Create one token bucket per IP
//Allow 5 login requests per minute
//On 6th request → return 429 Too Many Requests