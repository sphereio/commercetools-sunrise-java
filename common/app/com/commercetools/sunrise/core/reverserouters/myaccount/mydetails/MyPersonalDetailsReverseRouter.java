package com.commercetools.sunrise.core.reverserouters.myaccount.mydetails;

import com.commercetools.sunrise.core.reverserouters.ReverseRouter;
import com.google.inject.ImplementedBy;
import play.mvc.Call;

@ImplementedBy(DefaultMyPersonalDetailsReverseRouter.class)
public interface MyPersonalDetailsReverseRouter extends ReverseRouter {

    String MY_PERSONAL_DETAILS_PAGE = "myPersonalDetailsPageCall";

    Call myPersonalDetailsPageCall();

    String MY_PERSONAL_DETAILS_PROCESS = "myPersonalDetailsProcessCall";

    Call myPersonalDetailsProcessCall();
}
