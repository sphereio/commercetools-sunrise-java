package common.contexts;

import com.neovisionaries.i18n.CountryCode;
import common.prices.PriceFinder;
import common.utils.PriceFormatter;
import common.utils.Translator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.zones.Zone;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * A container for all information related to the current user, such as selected country, language or customer group.
 */
public class UserContext {
    private final CountryCode country;
    private final Locale language;
    private final List<Locale> fallbackLanguages;
    private final ZoneId zoneId;
    private final Reference<Zone> zone;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<Reference<Channel>> channel;
    private final CurrencyUnit currency;
    private final PriceFinder priceFinder;
    private final PriceFormatter priceFormatter;

    private UserContext(final CountryCode country, final Locale language, final List<Locale> fallbackLanguages, final ZoneId zoneId, final Reference<Zone> zone, final Reference<CustomerGroup> customerGroup, final Reference<Channel> channel) {
        this.country = country;
        this.language = language;
        this.fallbackLanguages = fallbackLanguages;
        this.zoneId = zoneId;
        this.zone = zone;
        this.customerGroup = Optional.ofNullable(customerGroup);
        this.channel = Optional.ofNullable(channel);
        this.currency = Monetary.getCurrency("EUR");
        this.priceFinder = PriceFinder.of(currency, country, this.customerGroup, this.channel);
        this.priceFormatter = PriceFormatter.of(language);
    }

    public static UserContext of(final CountryCode country, final Locale language, final List<Locale> fallbackLanguages, final ZoneId zoneId, final Reference<Zone> zone) {
        return new UserContext(country, language, fallbackLanguages, zoneId, zone, null, null);
    }

    public static UserContext of(final CountryCode country, final Locale language, final List<Locale> fallbackLanguages, final ZoneId zoneId, final Reference<Zone> zone, final Reference<CustomerGroup> customerGroup, final Reference<Channel> channel) {
        return new UserContext(country, language, fallbackLanguages, zoneId, zone, customerGroup, channel);
    }

    public CountryCode country() {
        return country;
    }

    public Locale language() {
        return language;
    }

    public List<Locale> fallbackLanguages() {
        return fallbackLanguages;
    }

    public ZoneId zoneId() {
        return zoneId;
    }

    public Reference<Zone> zone() {
        return zone;
    }

    public CurrencyUnit currency() {
        return currency;
    }

    public Optional<Reference<CustomerGroup>> customerGroup() {
        return customerGroup;
    }

    public Optional<Reference<Channel>> channel() {
        return channel;
    }

    public PriceFinder priceFinder() {
        return priceFinder;
    }

    public PriceFormatter priceFormatter() {
        return priceFormatter;
    }

    public Translator translator() {
        return Translator.of(language(), fallbackLanguages(), fallbackLanguages);
    }
}
