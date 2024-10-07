from django.urls import path
from . import views

urlpatterns = [
    path('', views.post_list, name='post_list'),  # 'post_list' 뷰를 메인 페이지에 연결합니다.
]

