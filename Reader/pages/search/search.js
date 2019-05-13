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
      inputShowed: false
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
      method: 'GET',
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
    console.log(item)
    
    wx.navigateTo({
      url: "/pages/reading/reading?book_id=" + item.novelId + "&website_id=9" + "&index_chapter=0" + "&book_title=" + item.title
    })
  }
})