package www.kaznu.kz.projects.m2.di;

import dagger.Component;
import www.kaznu.kz.projects.m2.repositories.MessageListFragmentRepository;

@Component(modules=ContextModule.class)
public interface AppComponent {
    void inject(MessageListFragmentRepository repository);
}
