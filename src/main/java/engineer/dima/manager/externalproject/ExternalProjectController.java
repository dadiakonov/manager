package engineer.dima.manager.externalproject;

import engineer.dima.manager.externalproject.view.ExternalProjectView;
import engineer.dima.manager.externalproject.view.ExternalProjectViewFactory;
import engineer.dima.manager.user.UserReader;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/external-projects")
public class ExternalProjectController {
    private final UserReader userReader;
    private final ExternalProjectReader externalProjectReader;
    private final ExternalProjectCreator externalProjectCreator;

    public ExternalProjectController(UserReader userReader,
                                     ExternalProjectReader externalProjectReader,
                                     ExternalProjectCreator externalProjectCreator) {
        this.userReader = userReader;
        this.externalProjectReader = externalProjectReader;
        this.externalProjectCreator = externalProjectCreator;
    }

    @GetMapping
    public Flux<ExternalProjectView> findAll(Authentication authentication) {

        return userReader.findByEmail((String) authentication.getPrincipal())
                .flatMapMany(user -> externalProjectReader.findAllByUserId(user.userId()))
                .map(ExternalProjectViewFactory::create);
    }

    @PostMapping
    public Mono<ExternalProjectView> create(@RequestBody @Valid ExternalProjectRequest externalProjectRequest,
                                            Authentication authentication) {
        return userReader.findByEmail((String) authentication.getPrincipal())
                .map(user -> ExternalProjectFactory.create(user.userId(), externalProjectRequest))
                .flatMap(externalProjectCreator::create)
                .map(ExternalProjectViewFactory::create);
    }
}
