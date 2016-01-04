# LoadMoreRecyclerView
##上拉加载的RecyclerView
######author Joe Chan

##因为项目需要去找了很多下拉刷新，上拉加载更多的RecyclerView，发现并不能符合我的需求，于是自己写了一个。

###翻看网上资料，大部分的给RecyclerView添加footView或者headView的方法就是通过一个itemViewType来判断即将加载的是普通item还是头尾view。
这个有个弊端就是在GridLayoutManager时候就没有用了，footView或者headVeiw就跑到第一个和最后一个Grid中去了。

###或者在Adapter中进行处理
这个需要通过LinearLayoutManager中findLastVisibleView的方法来判断是否到达最后。<br>
通过GridLayoutManager中的方法设置头尾view的Grid占比来使头尾变成一整行。
这种在adapter中处理的方法，不觉得还算是自定义view的范畴，顶多是recyclerView的活用。
<br>
##So
于是打算自己写了。使用组合View的方法，结合v4包中的NestedScrollingParent来处理和RecyclerView的滑动，<br>
然后使用NestedScrollingChild来处理嵌套在SwipeRefreshLayout中的滑动事件，达成使用SwipeRefreshLayout来进行下拉刷新。<br>
这样只要处理好滑动事件，那就不管是什么LayoutManager，footView永远在最下面。

#效果图
![](https://github.com/1030310877/LoadMoreRecyclerView/blob/master/demogif/grid-demo.gif)
![](https://github.com/1030310877/LoadMoreRecyclerView/blob/master/demogif/list-demo.gif)
