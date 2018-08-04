package org.springcloud.sidecar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalStatusDelegatorController {

    @Autowired
    private SidecarHealthIndicator healthIndicator;

    @RequestMapping("/delegating-status")
    public Health sidecarHealthStatus() {
        return this.healthIndicator.health();
    }
}