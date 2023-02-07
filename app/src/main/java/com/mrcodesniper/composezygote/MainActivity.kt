package com.mrcodesniper.composezygote

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrcodesniper.composezygote.data.sample_data
import com.mrcodesniper.composezygote.ui.theme.ComposeZygoteTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeZygoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ConversationList(sample_data)
                }
            }
        }
    }
}

data class Message(val body:String,val author:String)

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun MessageCard(message: Message) {
    //默认元素重叠展示
    //通过modifier设置间距
    Row(modifier = Modifier.padding(all = 8.dp)){
        Image(
            //设置大小40dp 以shape规则进行裁剪
            modifier = Modifier
                .size(40.dp)
                .border(1.5.dp, MaterialTheme.colors.onSecondary, CircleShape) //添加圆角外边框
                .clip(CircleShape),//默认的圆形shape
            painter = painterResource(id = R.drawable.empty_icon),
            contentDescription = "image")
        //间隔控件
        Spacer(modifier = Modifier.width(8.dp))
        //状态值 变化时更新UI
        var isExpanded by remember{
            mutableStateOf(false)
        }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )
        //每次点击修改状态值
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(text = "Hello ${message.author}!",color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
            //设置style样式 Typography排版
            Text(text = "Hello ${message.body}!", style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = surfaceColor,//颜色变换时的动画
                modifier = Modifier.animateContentSize().padding(1.dp),//可以在尺寸大小改变的时候添加动画
                shape = MaterialTheme.shapes.medium, elevation = 1.dp) { //一个面板用来做外围容器
                Text(text = "Hello ${message.body}!",
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2)
            }
        }

    }
}

/**
 * 列表展示
 */
@Composable
fun ConversationList(messages:List<Message>){
    LazyColumn{
        items(messages){ message ->
            MessageCard(message = message)
        }
    }
}


@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewMessageCard() {
//    MessageCard(
//        message = Message("Android","content")
//    )
    ConversationList(sample_data)
}