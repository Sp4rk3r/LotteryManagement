package be.thibaulthelsmoortel.lotterymanagement.services;

import be.thibaulthelsmoortel.lotterymanagement.i18n.Language;
import be.thibaulthelsmoortel.lotterymanagement.mappers.CookiePolicyMapper;
import be.thibaulthelsmoortel.lotterymanagement.model.CookiePolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static be.thibaulthelsmoortel.lotterymanagement.mappers.CookiePolicyMapper.GetCookiePolicyRequest;
import static be.thibaulthelsmoortel.lotterymanagement.mappers.CookiePolicyMapper.UpdateCookiePolicyRequest;

/**
 * Service for {@link CookiePolicy}.
 *
 * @author Thibault Helsmoortel
 */
@Service
public class CookiePolicyServiceImpl implements CookiePolicyService {

    private final CookiePolicyMapper mapper;

    @Autowired
    public CookiePolicyServiceImpl(CookiePolicyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CookiePolicy getCookiePolicy(Language language) {
        GetCookiePolicyRequest request = new GetCookiePolicyRequest();
        request.setLanguageCode(language.getCode());
        return mapper.getCookiePolicy(request);
    }

    @Override
    public void updateCookiePolicy(CookiePolicy policy) {
        UpdateCookiePolicyRequest request = new UpdateCookiePolicyRequest();
        request.setLanguageCode(policy.getLanguage().getCode());
        request.setPolicyHtml(policy.getPolicyHtml());
        mapper.updateCookiePolicy(request);
    }
}
