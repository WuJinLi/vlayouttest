package com.vlayout.wujinli.vlayout;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.FixAreaLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.vlayout.wujinli.vlayout.adapter.MyAdapter_DelegateAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyItemClickListener {
    private RecyclerView recycler_view;
    private List<HashMap<String, Object>> listItem;
    private MyAdapter_DelegateAdapter
                                        adapter_linearLayout,
                                        adapter_stickyLayout,
                                        adapter_scrollFixLayout,
                                        adapter_gridLayout,
                                        adapter_floatLayout,
                                        adapter_fixLayout,
                                        adapter_columnLayou,
                                        adapter_singleLayout,
                                        adapter_onePlusNLayout,
                                        adapter_staggeredGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 步骤1：创建RecyclerView & VirtualLayoutManager 对象并进行绑定
         */
        //初始化recyclerview对象
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        //初始化布局管理器

        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);


        /**
         * 步骤2：设置组件复用回收池
         */
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycler_view.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);


        /**
         * 步骤3:设置需要存放的数据
         * */
        listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "第" + i + "行");
            map.put("ItemImage", R.mipmap.ic_launcher);
            listItem.add(map);

        }


        /**
         * 步骤4:根据数据列表,创建对应的LayoutHelper
         */


        /**
         * 线性布局
         */
        //公共属性
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setItemCount(4);//设置item的个数
        linearLayoutHelper.setPadding(10, 10, 10, 10);//设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        linearLayoutHelper.setMargin(10, 10, 10, 10);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        linearLayoutHelper.setBgColor(Color.GRAY);//设置背景颜色
        linearLayoutHelper.setAspectRatio(6);//设置设置布局内每行布局的宽与高的比

        // linearLayoutHelper特有属性
        linearLayoutHelper.setDividerHeight(10); // 设置每行Item的距离

        //设置布局底部与下个布局的间隔
        linearLayoutHelper.setMarginBottom(100);


        /**
         * 步骤4:初始化适配器,
         *
         */

        adapter_linearLayout = new MyAdapter_DelegateAdapter(this, linearLayoutHelper, 4,
                listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                if (position == 0) {
                    holder.Item.setText("LinearLayout");
                }


                if (position < 2) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 6) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }
            }
        };

        adapter_linearLayout.setOnItemClickListener(this);


        /**
         * 设置吸边布局
         */

        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();

        //公共属性
        stickyLayoutHelper.setItemCount(3);
        stickyLayoutHelper.setPadding(20, 20, 20, 20);
        stickyLayoutHelper.setMargin(20, 20, 20, 20);
        stickyLayoutHelper.setBgColor(Color.GRAY);
        stickyLayoutHelper.setAspectRatio(3);

        // 特有属性
        stickyLayoutHelper.setStickyStart(true);     // true = 组件吸在顶部  false = 组件吸在底部
        stickyLayoutHelper.setOffset(10);// 设置吸边位置的偏移量


        adapter_stickyLayout = new MyAdapter_DelegateAdapter(this, stickyLayoutHelper, 1,
                listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.Item.setText("StickyLayout");
                }
            }
        };

        adapter_stickyLayout.setOnItemClickListener(this);


        /**
         *
         * 设置可选布局
         */


        // 参数说明:
        // 参数1:设置吸边时的基准位置(alignType) - 有四个取值:TOP_LEFT(默认), TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
        // 参数2:基准位置的偏移量x
        // 参数3:基准位置的偏移量y
        ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper
                (ScrollFixLayoutHelper.TOP_RIGHT, 0, 0);


        //公共属性

        scrollFixLayoutHelper.setItemCount(1);
        scrollFixLayoutHelper.setPadding(10, 10, 10, 10);
        scrollFixLayoutHelper.setMargin(10, 10, 10, 10);
        scrollFixLayoutHelper.setBgColor(Color.GRAY);
        scrollFixLayoutHelper.setAspectRatio(6);

        //scrollFixLayoutHelper特有属性
        scrollFixLayoutHelper.setAlignType(FixLayoutHelper.TOP_LEFT);// 设置吸边时的基准位置(alignType)
        scrollFixLayoutHelper.setX(10);// 设置基准位置的横向偏移量X
        scrollFixLayoutHelper.setY(10);// 设置基准位置的纵向偏移量Y
        scrollFixLayoutHelper.setShowType(ScrollFixLayoutHelper.SHOW_ON_LEAVE);


        adapter_scrollFixLayout = new MyAdapter_DelegateAdapter(this, scrollFixLayoutHelper, 1,
                listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.Item.setText("ScrollFixLayout");
                }
            }
        };

        adapter_scrollFixLayout.setOnItemClickListener(this);


        /**
         * 设置Grid布局
         */

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);

        //公共属性
        gridLayoutHelper.setItemCount(36);//设置item的个数
        gridLayoutHelper.setPadding(20, 20, 20, 20);//子控件内容相对子控件的间距
        gridLayoutHelper.setMargin(20, 20, 20, 20);//设置子布局相对于父布局的距离
        gridLayoutHelper.setAspectRatio(6);//设置每行宽高比
        gridLayoutHelper.setBgColor(Color.GRAY);

        //gridLayoutHelper特有属性
        gridLayoutHelper.setWeights(new float[]{40, 30, 30});//设置每行中 每个网格宽度 占 每行总宽度 的比例
        gridLayoutHelper.setVGap(20);// 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(20);// 控制子元素之间的水平间距
        gridLayoutHelper.setAutoExpand(false);//是否自动填充空白区域
        gridLayoutHelper.setSpanCount(3);// 设置每行多少个网格

        // 通过自定义SpanSizeLookup来控制某个Item的占网格个数
        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
            //第7个位置之后占据3个网格，7个之前的占据2个网格
            @Override
            public int getSpanSize(int position) {
                if (position > 7) {
                    return 3;
                } else {
                    return 2;
                }
            }
        });

        adapter_gridLayout = new MyAdapter_DelegateAdapter(this, gridLayoutHelper, 36, listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                if (position < 2) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 6) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }

                if (position == 0) {
                    holder.Item.setText("GridLayout");
                }
            }
        };

        adapter_gridLayout.setOnItemClickListener(this);


        /**
         * 设置浮动布局
         */

        FloatLayoutHelper floatLayoutHelper = new FloatLayoutHelper();

        //共有属性
        floatLayoutHelper.setBgColor(Color.GRAY);
        floatLayoutHelper.setPadding(20, 20, 20, 20);
        floatLayoutHelper.setMargin(20, 20, 20, 20);
        floatLayoutHelper.setItemCount(1);
        floatLayoutHelper.setAspectRatio(6);


        //特有属性

        floatLayoutHelper.setDefaultLocation(300, 300);// 设置布局里Item的初始位置

        adapter_floatLayout = new MyAdapter_DelegateAdapter(this, floatLayoutHelper, 1, listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.Item.setText("FloatLayout");
                }
            }
        };

        adapter_floatLayout.setOnItemClickListener(this);


        /**
         * 设置固定布局
         */

        // 参数说明:
        // 参数1:设置吸边时的基准位置(alignType) - 有四个取值:TOP_LEFT(默认), TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
        // 参数2:基准位置的偏移量x
        // 参数3:基准位置的偏移量y
        FixLayoutHelper fixLayoutHelper = new FixLayoutHelper(FixLayoutHelper.TOP_LEFT, 40, 100);

        fixLayoutHelper.setItemCount(1);
        // 从设置Item数目的源码可以看出，一个FixLayoutHelper只能设置一个
        //  @Override
        //  public void setItemCount(int itemCount) {
        //      if (itemCount > 0) {
        //              super.setItemCount(1);
        //      else {
        //              super.setItemCount(0);
        //          }
        //      }
        fixLayoutHelper.setBgColor(Color.GRAY);
        fixLayoutHelper.setPadding(20, 20, 20, 20);
        fixLayoutHelper.setMargin(20, 20, 20, 20);
        fixLayoutHelper.setAspectRatio(6);

        // fixLayoutHelper特有属性

        fixLayoutHelper.setAlignType(FixLayoutHelper.TOP_RIGHT);// 设置吸边时的基准位置(alignType)
        fixLayoutHelper.setX(30);
        fixLayoutHelper.setY(30);

        adapter_fixLayout = new MyAdapter_DelegateAdapter(this, fixLayoutHelper, 1, listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.Item.setText("FixLayout");
                }
            }
        };

        adapter_fixLayout.setOnItemClickListener(this);


        /**
         * 设置栏格布局
         */

        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();

        //公共属性
        columnLayoutHelper.setItemCount(3);
        columnLayoutHelper.setPadding(20, 20, 20, 20);
        columnLayoutHelper.setMargin(20, 20, 20, 20);
        columnLayoutHelper.setBgColor(Color.GRAY);
        columnLayoutHelper.setAspectRatio(6);

        //特有属性
        columnLayoutHelper.setWeights(new float[]{30, 40, 30});//设置每个item 的占该行宽度的总比例

        adapter_columnLayou = new MyAdapter_DelegateAdapter(this, columnLayoutHelper, 3, listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.Item.setText("ColumnLayout");
                }
            }
        };

        adapter_columnLayou.setOnItemClickListener(this);

        /**
         * 设置通栏布局
         */

        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();

        //公共属性

        singleLayoutHelper.setItemCount(3);
        singleLayoutHelper.setPadding(20, 20, 20, 20);
        singleLayoutHelper.setMargin(20, 20, 20, 20);
        singleLayoutHelper.setAspectRatio(6);
        singleLayoutHelper.setBgColor(Color.GRAY);

        adapter_singleLayout = new MyAdapter_DelegateAdapter(this, singleLayoutHelper, 3,
                listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.Item.setText("SingleLayout");
                }
            }
        };

        adapter_singleLayout.setOnItemClickListener(this);


        /**
         *  设置1拖N布局
         */

        // 在构造函数里传入显示的Item数
        // 最多是1拖4,即5个
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(5);

        onePlusNLayoutHelper.setItemCount(3);
        onePlusNLayoutHelper.setPadding(20, 20, 20, 20);
        onePlusNLayoutHelper.setMargin(20, 20, 20, 20);
        onePlusNLayoutHelper.setBgColor(Color.GRAY);
        onePlusNLayoutHelper.setAspectRatio(3);

        adapter_onePlusNLayout = new MyAdapter_DelegateAdapter(this, onePlusNLayoutHelper, 5,
                listItem) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position == 0) {
                    holder.Item.setText("OnePlusNLayout");
                }
            }
        };

        adapter_onePlusNLayout.setOnItemClickListener(this);


        /**
         * 设置瀑布流布局
         */

        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper();

        staggeredGridLayoutHelper.setItemCount(20);
        staggeredGridLayoutHelper.setPadding(20, 20, 20, 20);//
        // 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        staggeredGridLayoutHelper.setMargin(20, 20, 20, 20);
        //设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        staggeredGridLayoutHelper.setBgColor(Color.GRAY);
        staggeredGridLayoutHelper.setAspectRatio(3);//设置设置布局内每行布局的宽与高的比

        // 特有属性
        staggeredGridLayoutHelper.setLane(3);// 设置控制瀑布流每行的Item数
        staggeredGridLayoutHelper.setHGap(20);// 设置子元素之间的水平间距
        staggeredGridLayoutHelper.setVGap(15);// 设置子元素之间的垂直间距

        adapter_staggeredGridLayout = new MyAdapter_DelegateAdapter(this,
                staggeredGridLayoutHelper, 20, listItem) {

            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup
                        .LayoutParams.MATCH_PARENT, 150 + position % 5 * 20);
                holder.itemView.setLayoutParams(layoutParams);


                // 为了展示效果,设置不同位置的背景色
                if (position > 10) {
                    holder.itemView.setBackgroundColor(0x66cc0000 + (position - 6) * 128);
                } else if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(0xaa22ff22);
                } else {
                    holder.itemView.setBackgroundColor(0xccff22ff);
                }

                if (position == 0) {
                    holder.Item.setText("StaggeredGridLayout");
                }
            }
        };

        adapter_staggeredGridLayout.setOnItemClickListener(this);


        /**
         * 步骤5:将生成的LayoutHelper 交给Adapter，并绑定到RecyclerView 对象
         */
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();

        adapters.add(adapter_linearLayout);
        adapters.add(adapter_scrollFixLayout);
        adapters.add(adapter_gridLayout);
        adapters.add(adapter_stickyLayout);
        adapters.add(adapter_floatLayout);
        adapters.add(adapter_fixLayout);
        adapters.add(adapter_columnLayou);
        adapters.add(adapter_singleLayout);
        adapters.add(adapter_onePlusNLayout);
        adapters.add(adapter_staggeredGridLayout);

        DelegateAdapter adapter = new DelegateAdapter(layoutManager);

        adapter.setAdapters(adapters);

        recycler_view.setAdapter(adapter);

        /**
         *
         * 步骤7:设置分割线 & Item之间的间隔
         **/
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager
        // .getOrientation()));
        // 需要自定义类DividerItemDecoration


        recycler_view.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                outRect.set(5, 5, 5, 5);
            }
        });

//        recycler_view.addItemDecoration(new DividerItemDecoration(this, layoutManager
//                .getOrientation()));

    }

    @Override
    public void onItemClick(View view, int postion) {
        Toast.makeText(this, (String) listItem.get(postion).get("ItemTitle"), Toast.LENGTH_SHORT)
                .show();
    }
}
