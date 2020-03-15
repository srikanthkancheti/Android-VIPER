package android.com.viper.ui.mealDetail;

import android.com.viper.model.network.interceptor.NetworkAvailabilityMonitor;
import android.com.viper.model.response.meals.MealDetailsModel;
import android.com.viper.model.response.meals.MealDetailsResponse;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MealDetailPresenterTest {
  @Mock private MealDetailInteractor catDetailInteractor;
  @Mock private NetworkAvailabilityMonitor networkAvailabilityMonitor;
  @Mock private MealDetailView view;

  private MealDetailPresenter presenter;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    presenter = new MealDetailPresenter(catDetailInteractor, networkAvailabilityMonitor);
    presenter.takeMealImageId("52772");
  }

  @Test @SuppressWarnings("unchecked") public void testTakeViewInteractorInvoked() {
    //When
    presenter.takeView(view);
    //Then
    Mockito.verify(catDetailInteractor).execute(any(Subscriber.class),
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
    Mockito.verify(presenter, Mockito.never()).onMealDetailsReceived(any(MealDetailsModel.class));
    Mockito.verify(presenter, Mockito.never()).onMealDetailError(ArgumentMatchers.anyString());
    subscriber.getValue().onNext(Mockito.mock(MealDetailsResponse.class));
    subscriber.getValue().onError(Mockito.mock(Throwable.class));
    Mockito.verify(presenter).onMealDetailError(ArgumentMatchers.anyString());
  }

  @Test public void testMealDetailViewUpdated() {
    //Given
    MealDetailsModel mealDetailsModel = mock(MealDetailsModel.class);
    presenter.takeView(view);
    //When
    presenter.onMealDetailsReceived(mealDetailsModel);
    //Then
    verify(view).showMealDetailsView(mealDetailsModel);
  }
}
