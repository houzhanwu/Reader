// pages/bookshelf/bookshelf.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bookshelf: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.getBookshelf()
  },
  getBookshelf: function() {
    var bookshelf = wx.getStorageSync('bookshelf')
    this.setData({
      bookshelf: bookshelf
    })
  },
  
  jump: function (o) {
    var item = o.currentTarget.dataset.object
    wx.navigateTo({
      url: "/pages/reading/reading?book_id=" + item.book_id + "&website_id=" + item.website_id + "&index_chapter=" + item.index_chapter + "&book_title=" + item.book_title
    })
  }
})