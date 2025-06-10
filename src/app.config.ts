export default defineAppConfig({
  pages: [
    'pages/index/index',
    'pages/hero/hero',
    'pages/hero/detail',
    'pages/post/post',
    'pages/post/detail',
    'pages/pal/pal',
    'pages/pal/question',
    'pages/service/service',
    'pages/about/about',
    'pages/about/login',
    'pages/about/register',
    'pages/about/profile',
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#8c2525',
    navigationBarTitleText: '红星耀国防平台',
    navigationBarTextStyle: 'white'
  },
  tabBar: {
    backgroundColor: '#a33838',
    selectedColor: '#000000',
    color: '#000000',
    list: [
      {pagePath: 'pages/index/index', text: "首页", iconPath: "assets/image/home.png", selectedIconPath: "assets/image/home-selected.png"},
      {pagePath: 'pages/about/about', text: "我的", iconPath: "assets/image/user.png", selectedIconPath: "assets/image/user-selected.png"},
    ]
  }
})
