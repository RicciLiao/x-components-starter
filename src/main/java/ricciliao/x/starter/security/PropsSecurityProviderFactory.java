package ricciliao.x.starter.security;

import ricciliao.x.component.security.SecurityProvider;
import ricciliao.x.component.security.SecurityProviderFactory;
import ricciliao.x.component.sneaky.SneakyThrowUtils;
import ricciliao.x.component.utils.CoreUtils;

import java.util.Objects;

public class PropsSecurityProviderFactory implements SecurityProviderFactory {

    private final boolean active;

    public PropsSecurityProviderFactory() {
        SecurityAutoProperties props = CoreUtils.convert2Properties(SecurityAutoProperties.class);
        this.active = Objects.nonNull(props) && props.isActive();
    }

    @Override
    public SecurityProvider createProvider() {

        return SneakyThrowUtils.get(PropsSecurityProvider::new);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean active() {

        return this.active;
    }

}
