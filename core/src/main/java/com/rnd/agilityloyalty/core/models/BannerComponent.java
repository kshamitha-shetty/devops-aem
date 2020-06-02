package com.rnd.agilityloyalty.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rnd.agilityloyalty.core.utils.CommonUtil;
import com.rnd.agilityloyalty.core.utils.LinksUtil;
import com.rnd.agilityloyalty.core.vo.CarouselImageVO;

/**
 * The Class HeaderModel.
 *
 * @author ajena
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BannerComponent {

	private static final Logger log = LoggerFactory
			.getLogger(BannerComponent.class);

    private List<CarouselImageVO> bannerList;

    @ValueMapValue
    private String[] bannerItems;

	@SlingObject
	private ResourceResolver resourceResolver;

	@SlingObject
	private Resource resource;

	/**
     * This method reads the Carousel multi field values
     * and prepares the list.
     */
    @PostConstruct
    protected void init() {
        log.info("BannerComponent :: init :: Start");
        bannerList = new ArrayList<>();
        if (ArrayUtils.isNotEmpty(bannerItems)) {            
            for (final String data : bannerItems) {
                final CarouselImageVO carouselVo = (CarouselImageVO) CommonUtil.getObjectFromJson(data, new CarouselImageVO());
                carouselVo.setLinkUrl(LinksUtil.checkInternalURLByPath(carouselVo.getLinkUrl(), resourceResolver));
                bannerList.add(carouselVo);
            }
        } else {
            log.error("BannerComponent :: Empty List");
        }
        log.info("BannerComponent :: init :: End");
    }

    public List<CarouselImageVO> getBannerList() {
        return bannerList;
    }
}
