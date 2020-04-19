package com.dbz.view.fewview;

import java.io.Serializable;

public final class FewBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private int iconRes;//菜单图标 没有图标置为-1
    private String title;//菜单标题
    private int moreIconRes;//更多图标
    private boolean more;//是否显示更多
    private String moreText;//更多提示的内容
    private boolean showMoreText;//是否显示更多文字提示

    /**
     * @param iconRes      图标
     * @param title        菜单名称
     * @param moreIconRes  更多图标
     * @param more         是否显示更多图标
     * @param moreText     更多提示文字
     * @param showMoreText 是否显示更多提示文字
     */
    public FewBean(int iconRes, String title, int moreIconRes, boolean more, String moreText, boolean showMoreText) {
        this.iconRes = iconRes;
        this.title = title;
        this.moreIconRes = moreIconRes;
        this.more = more;
        this.moreText = moreText;
        this.showMoreText = showMoreText;
    }

    /**
     * 没有图标
     *
     * @param title
     * @param moreIconRes
     * @param more
     * @param moreText
     * @param showMoreText
     */
    public FewBean(String title, int moreIconRes, boolean more, String moreText, boolean showMoreText) {
        this.iconRes = -1;
        this.title = title;
        this.moreIconRes = moreIconRes;
        this.more = more;
        this.moreText = moreText;
        this.showMoreText = showMoreText;
    }

    /**
     * 没有图标且没有更多提示
     *
     * @param title
     * @param moreIconRes
     * @param more
     */
    public FewBean(String title, int moreIconRes, boolean more) {
        this.iconRes = -1;
        this.title = title;
        this.moreIconRes = moreIconRes;
        this.more = more;
        this.moreText = "";
        this.showMoreText = false;
    }


    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMoreIconRes() {
        return moreIconRes;
    }

    public void setMoreIconRes(int moreIconRes) {
        this.moreIconRes = moreIconRes;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public String getMoreText() {
        return moreText;
    }

    public void setMoreText(String moreText) {
        this.moreText = moreText;
    }

    public boolean isShowMoreText() {
        return showMoreText;
    }

    public void setShowMoreText(boolean showMoreText) {
        this.showMoreText = showMoreText;
    }
}
