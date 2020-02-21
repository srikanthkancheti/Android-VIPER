package android.com.viper.ui.catDetail;

import android.com.viper.model.network.interceptor.NetworkAvailabilityMonitor;
import android.com.viper.model.response.CatDetailModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import rx.Subscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CatDetailPresenterTest {

  @Mock private CatDetailInteractor catDetailInteractor;
  @Mock private NetworkAvailabilityMonitor networkAvailabilityMonitor;
  @Mock private CatDetailViewCallBacks view;

  private CatDetailPresenter presenter;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    presenter = new CatDetailPresenter(catDetailInteractor, networkAvailabilityMonitor);
    presenter.takeCatImageId("1dc");
  }

  @Test @SuppressWarnings("unchecked") public void testTakeViewInteractorInvoked() {
    //When
    presenter.takeView(view);
    //Then
    Mockito.verify(catDetailInteractor).execute(ArgumentMatchers.any(Subscriber.class),
        ArgumentMatchers.anyString());
  }

  @Test @SuppressWarnings("unchecked") public void testTakeViewCorrectCallbacksPassed() {
    //Given
    presenter = Mockito.spy(presenter);
    ArgumentCaptor<Subscriber> subscriber = ArgumentCaptor.forClass(Subscriber.class);
    //When
    presenter.takeView(view);
    //Then
    Mockito.verify(catDetailInteractor).execute(subscriber.capture(), ArgumentMatchers.anyString());
    Mockito.verify(presenter, Mockito.never()).onCatImageDetailsReceived(ArgumentMatchers.any(CatDetailModel.class));
    Mockito.verify(presenter, Mockito.never()).onCatDetailError(ArgumentMatchers.anyString());
    subscriber.getValue().onNext(Mockito.mock(CatDetailModel.class));
    Mockito.verify(presenter).onCatImageDetailsReceived(ArgumentMatchers.any(CatDetailModel.class));
    subscriber.getValue().onError(Mockito.mock(Throwable.class));
    Mockito.verify(presenter).onCatDetailError(ArgumentMatchers.anyString());
  }

  @Test public void testCatImageDetailViewUpdated() {
    //Given
    CatDetailModel catDetailModel = mock(CatDetailModel.class);
    presenter.takeView(view);
    //When
    presenter.onCatImageDetailsReceived(catDetailModel);
    //Then
    verify(view).showCatImageView(catDetailModel);
  }
}
