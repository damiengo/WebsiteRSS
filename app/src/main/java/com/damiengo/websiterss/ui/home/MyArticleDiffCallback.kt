package com.damiengo.websiterss.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.damiengo.websiterss.article.MyArticle

class MyArticleDiffCallback(_oldList: MutableList<MyArticle>, _newList: MutableList<MyArticle>) : DiffUtil.Callback() {

    private var oldList: MutableList<MyArticle> = _oldList
    private var newList: MutableList<MyArticle> = _newList

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].article.guid.equals(newList[newItemPosition].article.guid)
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].article.link.equals(newList[newItemPosition].article.link)
    }

}
