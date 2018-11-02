package vpay.homtech.vn.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(vpay.homtech.vn.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.ProductType.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.ProductType.class.getName() + ".productTypes", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.Partner.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.Partner.class.getName() + ".partners", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.PayPartner.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.PayPartner.class.getName() + ".payPartners", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.WalletTransactionType.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.WalletTransactionType.class.getName() + ".walletTransTypes", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.Wallet.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.Wallet.class.getName() + ".wallets", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.WalletRule.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.WalletRule.class.getName() + ".walletRules", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.WalletRuleRate.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.WalletTransaction.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.WalletTransaction.class.getName() + ".walletTransactions", jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.PartnerTransaction.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.PayPartnerLog.class.getName(), jcacheConfiguration);
            cm.createCache(vpay.homtech.vn.domain.PartnerLog.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
