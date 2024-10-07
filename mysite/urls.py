from django.contrib import admin
from django.urls import path, include  # include를 추가합니다.
from django.urls import path

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', include('blog.urls')),  # '' 경로로 blog.urls를 포함시킵니다.
]
