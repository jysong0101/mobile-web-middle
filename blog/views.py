from django.shortcuts import render
from .models import Post

def post_list(request):
    posts = Post.objects.all()  # 모든 게시물을 가져옴
    return render(request, 'blog/post_list.html', {'posts': posts})
