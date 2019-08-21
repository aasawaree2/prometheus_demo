package main.java.demo;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.Writer;

@Controller
@SpringBootApplication
public class Demo {

    private final Counter promRequestsTotal = Counter.build()
            .name("hits")
            .help("Total number of hits.")
            .register();

    @RequestMapping(path = "/hits")
    public @ResponseBody String sayHello() {
        promRequestsTotal.inc();
        return "Hits";
    }

    @RequestMapping(path = "/prometheus")
    public void metrics(Writer responseWriter) throws IOException {
        TextFormat.write004(responseWriter, CollectorRegistry.defaultRegistry.metricFamilySamples());
        responseWriter.close();
    }

    public static void main(String[] args) {
        SpringApplication.run(Demo.class, args);
    }
}