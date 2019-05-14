// pages/search/search.js
Page({

  data: {
    inputShowed: false,
    inputVal: "",
    result: ''
  },
  showInput: function() {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function() {
    this.setData({
      inputVal: "",
      inputShowed: false,
      result: ''
    });
  },
  clearInput: function() {
    this.setData({
      inputVal: ""
    });
  },
  inputTyping: function(e) {
    this.setData({
      inputVal: e.detail.value
    });
    wx.showLoading({
      title: '搜索中。。。',
      mask: true,
    })
    this.searchTitle(e.detail.value)
  },

  searchTitle: function(title) {
    var page = this
    wx.request({
      url: getApp().baseUrl + "search",
      data: {
        title: title
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      method: 'POST',
      success(res) {
        wx.hideLoading()
        page.setData({
          result: res.data
        })
      }
    })
  },

  jump: function(o) {
    var item = o.currentTarget.dataset.object
    var bookshelf = wx.getStorageSync("bookshelf")
    var bookinfo={
      book_id: item.novelId,
      book_title: item.title,
      book_author: item.author,
      book_description: item.description,
      book_category: item.category,
      book_isEnd: item.isEnd,
      website_id: 9,
      index_chapter: 0,
      chapter_title:'',
    }
    var boolean =false
    for (var i = 0 ; i < bookshelf.length ; i++){
      if(bookshelf[i].book_id == bookinfo.book_id){
        bookinfo.index_chapter = bookshelf[i].index_chapter
        bookinfo.chapter_title = bookshelf[i].chapter_title
        bookshelf[i] = bookinfo
        boolean = true
        break
      }
    }
    if(!boolean){
      bookshelf.push(bookinfo)
    }
    wx.setStorageSync('bookshelf', bookshelf)
    wx.navigateTo({
      url: "/pages/reading/reading?book_id=" + bookinfo.book_id + "&website_id="+bookinfo.website_id + "&index_chapter=" + bookinfo.index_chapter + "&book_title=" + bookinfo.book_title
    })
  }
})