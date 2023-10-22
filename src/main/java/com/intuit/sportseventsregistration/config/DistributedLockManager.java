package com.intuit.sportseventsregistration.config;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class DistributedLockManager {
    private final Map<String, Lock> locks;

    public DistributedLockManager() {
        locks = new ConcurrentHashMap<>();
    }

    public void acquireLock(String lockKey) throws InterruptedException, TimeoutException {
        Lock lock = locks.computeIfAbsent(lockKey, key -> new ReentrantLock());
        lock.lockInterruptibly();
    }

    public void releaseLock(String lockKey) {
        Lock lock = locks.remove(lockKey);
        if (lock != null) {
            lock.unlock();
        }
    }
}
