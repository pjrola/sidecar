package org.springcloud.sidecar;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticSearchHealthCheck implements SidecarHealthIndicator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Health health() {

        Health.Builder result = null;

        try (TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300))) {
            ClusterHealthResponse response = client.admin().cluster().prepareHealth().get();

            switch (response.getStatus()) {
                case GREEN:
                case YELLOW:
                    result = Health.up();
                    break;
                case RED:
                default:
                    result = Health.down();
                    break;
            }

        } catch (UnknownHostException e) {
            log.error("error connecting to es client", e);
            result = Health.down().withException(e);
        }

        return result.build();
    }
}