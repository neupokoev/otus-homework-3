package support;

import com.google.inject.AbstractModule;
import services.IUserApi;
import services.UserApi;

public class UserModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(IUserApi.class).to(UserApi.class);
  }

}
