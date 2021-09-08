<template>
  <div class="pagination" role="group" aria-label="分页">
    <button
      type="button"
      class="btn btn-default btn-white btn-round"
      v-bind:disabled="page === 1"
      v-on:click="selectPage(1)"
    >
      1
    </button>
    <button
      type="button"
      class="btn btn-default btn-white btn-round"
      v-bind:disabled="page === 1"
      v-on:click="selectPage(page - 1)"
    >
      上一页
    </button>
    <button
      v-for="p in pages"
      v-bind:id="'page-' + p"
      type="button"
      class="btn btn-default btn-white btn-round"
      v-bind:class="{ 'btn-primary active': page == p }"
      v-on:click="selectPage(p)"
      v-bind:key="p.index"
    >
      {{ p }}
    </button>
    <button
      type="button"
      class="btn btn-default btn-white btn-round"
      v-bind:disabled="page === pageTotal"
      v-on:click="selectPage(page + 1)"
    >
      下一页
    </button>
    <button
      type="button"
      class="btn btn-default btn-white btn-round"
      v-bind:disabled="page === pageTotal"
      v-on:click="selectPage(pageTotal)"
    >
      {{ pageTotal || 1 }}
    </button>
    &nbsp;
    <span class="m--padding-10">
      每页
      <select v-model="size" v-on:change="changeSizeNumber(page)">
        <option value="1">1</option>
        <option value="5">5</option>
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="50">50</option>
        <option value="100">100</option>
      </select>
      条，共【{{ total }}】条
    </span>
  </div>
</template>

<script>
export default {
  name: "pagination",
  props: {
    list: {
      type: Function,
      default: null,
    },
    itemCount: Number, // page number
  },

  data: function () {
    return {
      total: 0, // total record number
      size: 10, // every page's record number
      page: 0, // current page number
      pageTotal: 0, // total page number
      pages: [], // pages array used to display on page
    };
  },

  methods: {
    /**
     * rerender page
     * @param page
     * @param total
     */
    render(page, total) {
      
      let _this = this;
      _this.page = page;
      _this.total = total;
      _this.pageTotal = Math.ceil(total / _this.size);
      _this.pages = _this.getPageItems(
        _this.pageTotal,
        page,
        _this.itemCount || 5
      );
    },

    /**
     * select one page record
     * @param page
     */
    selectPage(page) {
      let _this = this;
      if (page < 1) {
        page = 1;
      }
      if (page > _this.pageTotal) {
        page = _this.pageTotal;
      }
      if (_this.page !== page) {
        _this.page = page;
        if (_this.list) {
          // call the method transfered by parent component
          _this.list(page);
        }
      }
    },

    /**
     * update page number should be displayed on the current page
       * @param pageTotal
       * @param page
       * @param itemCount
       * @returns {Array}
     */
    getPageItems(pageTotal, page, itemCount) {
      let items = [];

      // assume that current page is the center position, and according to itemCount, the number of pages in left side of center 
      // postion is just like following:
      // 1 2 3(center postion) 4 5  left side == right side when itemCount is odd
      // 1 2(center postion) 3 4  left side is less one than right side when itemCount is even
      // we just calculate the left start index then we can get all pages number should be displayed
      let currentPageLeftNumber = Math.floor((itemCount - 1) / 2);
      let startIndex = page - currentPageLeftNumber;
      let endIndex;

      if (startIndex <= 0) {
        startIndex = 1;
      }
      endIndex = Math.min(pageTotal, startIndex -1 + itemCount);
      
      for (let i = startIndex; i <= endIndex; i++){
       items.push(i);
      }
      return items;
    },

    /**
     * reselect records when size changed
     */
    changeSizeNumber(page) {
      let _this = this;
      if (page * _this.size > _this.total) {
        page = 1;
      }
      _this.list(page);
    },
  },
};
</script>

<style scoped>
.pagination {
  vertical-align: middle !important;
  font-size: 16px;
  margin-top: 0;
  margin-bottom: 10px;
}

.pagination button {
  margin-right: 5px;
}

.btn-primary.active {
  background-color: #2f7bba !important;
  border-color: #27689d !important;
  color: white !important;
  font-weight: 600;
}

/*.pagination select {*/
/*vertical-align: middle !important;*/
/*font-size: 16px;*/
/*margin-top: 0;*/
/*}*/
</style>
