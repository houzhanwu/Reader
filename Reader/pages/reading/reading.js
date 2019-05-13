Page({

  /**
   * 页面的初始数据
   */
  data: {

    
    //页面信息
    book_id: '', //存储小说表中的ID
    website_id: "", //小说网站对应的ID
    index_chapter: 0, //章节在目录中的index
    chapter_url: "", //当前章节的url
    book_title: '', //小说的标题
    chapters: '', // 存储小说目录信息
    content: '', //当前章节内容
    title: '', //当前章节标题

    //页面逻辑
    showPage: false,
    showCatalog: false,
    showMenu: false,
    isDark:false,
    isHuyan:false,

    //页面css
    titleSize: '150%',
    titleHeight: '200%',
    contentSize: '100%',
    contentHeight: '150%',
    color: '#333',
    backgroundColor: '#fff',
    scrollTop: 0,


    // 设备信息
    clientHeight: '',
    clientWidth: '',
    winHeight: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.showLoading({
      title: '点击呼出菜单',
      mask: true
    });
    this.setData({
      book_id: options.book_id,
      website_id: options.website_id, //小说网站对应的ID
      index_chapter: options.index_chapter, //章节在目录中的index
      book_title: options.book_title, //小说的标题
    })
    wx.setNavigationBarTitle({
      title: this.data.book_title
    })
    this.getChapterList();
    var page = this
    wx.getSystemInfo({
      success(res) {
        var clientHeight = res.windowHeight,
          clientWidth = res.windowWidth,
          rpxR = 750 / clientWidth;
        var calc = clientHeight * rpxR;
        page.setData({
          clientHeight: clientHeight,
          clientWidth: clientWidth,
          winHeight: calc,
        });
      }
    });

    setTimeout(() => {
      
    }, 4000);

  },


  openMenu: function(options) {
    this.setData({
      showMenu: !this.data.showMenu
    });
  },

  showChapter: function(options) {
    this.setData({
      showCatalog: true,
      showMenu: false,
      scrollTop: 0
    })
  },
  closeChapter: function(options) {
    this.setData({
      showCatalog: false,
    })
  },

  goPrev: function(o) {
    if (this.data.index_chapter === 0) {
      wx.showToast({
        title: '已到第一章',
        icon: 'loading',
        mask: true
      });
    } else {
      var index = parseInt(this.data.index_chapter) - parseInt(1)
      this.setData({
        index_chapter: index,
        scrollTop: 0
      });
      if (this.data.chapters[this.data.index_chapter]) {
        this.getChapterContent(this.data.chapters[this.data.index_chapter].url);
      }
    }
  },

 

  //跳转下一章
  goNext: function(o) {
    if (this.data.index_chapter == this.data.chapters.length - 1) {
      wx.showToast({
        title: '已到最新章节',
        icon: 'loading',
        mask: true
      });
    } else {
      var index = parseInt(this.data.index_chapter) +parseInt(1)
      console.log(index)
      this.setData({
        index_chapter:index,
        scrollTop: 0
      });
      if (this.data.chapters[index]) {
        this.getChapterContent(this.data.chapters[index].url);
        this.setPageStorage(index)
      }
    }
  },

  //切换夜间模式
  toggleDark: function(o) {
    this.setData({
      isDark: !this.data.isDark
    });
    if (this.data.isDark) {
      wx.setNavigationBarColor({
        frontColor: '#ffffff',
        backgroundColor: '#080C10'
      });
      
      this.setData({
        isHuyan: false,
        color : '#696969',
        backgroundColor : '#080C10',
      });
    } else {
      wx.setNavigationBarColor({
        frontColor: '#ffffff',
        backgroundColor: '#8ed145'
      });

      this.setData({
        isHuyan: false,
        color : '#333',
        backgroundColor : '#fff',
      });
    }
  },

  //切换到护眼模式
  toggleHuyan: function(o) {
    this.setData({
      isHuyan: !this.data.isHuyan
    });
    if (this.data.isHuyan) {
      wx.setNavigationBarColor({
        frontColor: '#ffffff',
      });
      
      this.setData({
        isDark: false,
        color : '#333',
        backgroundColor : '#C7EDCC',
      });
    } else {
      wx.setNavigationBarColor({
        frontColor: '#ffffff',
      });
      
      this.setData({
        isDark: false,
        color : '#333',
        backgroundColor : '#fff',
      });
    }
  }, 

  //下载页面
  getChapterContent: function (link) {
    wx.showLoading({
      title: '加载中',
      mask: true
    })
    var page = this;
    
    wx.request({
      url: getApp().baseUrl + "getContent",
      data: {
        website_id: this.data.website_id,
        url: link,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      method: 'POST',
      success(res) {
        wx.hideLoading(),
        page.setData({
          content: res.data.content,
          title: res.data.title,
          showPage: true
        })
        page.setPageStorage()
      }
    })
    
  },

  setPageStorage: function(){
    //保存看的书信息
    var bookshelf = wx.getStorageSync('bookshelf')
    var index = this.data.index_chapter
    var title = this.data.title 
    for (var i = 0; i < bookshelf.length; i++) {
      if (bookshelf[i].book_id == this.data.book_id) {
        bookshelf[i].index_chapter = index
        bookshelf[i].chapter_title = title
        wx.setStorageSync('bookshelf', bookshelf)
        break
      }
    }
    console.log(bookshelf)
    console.log("--------------")
    console.log(wx.getStorageSync('bookshelf'))
  },

  //下载章节列表
  getChapterList: function () {
    var page = this
    wx.request({
      url: getApp().baseUrl + "/getCatalog",
      data: {
        website_id: this.data.website_id,
        book_id: this.data.book_id,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      method: 'GET',
      success(res) {
        page.setData({
          chapters: res.data,
        })
        page.getChapterContent(res.data[page.data.index_chapter].url)
      },
    })
    
  },

  //点击目录列表
  pickChapter: function (o) {
    var index = o.currentTarget.dataset.indexpage;
    var link = o.currentTarget.dataset.link;
    this.setData({
      index_chapter: index,
      chapter_url: link,
      showCatalog: false,
    })
    this.getChapterContent(link)
    this.setPageStorage(index)
  },
})