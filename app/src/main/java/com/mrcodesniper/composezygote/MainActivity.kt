package com.mrcodesniper.composezygote

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrcodesniper.composezygote.ui.theme.ComposeZygoteTheme

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
                    Greeting("Android")
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
        Column {
            Text(text = "Hello ${message.author}!",color = MaterialTheme.colors.error)
            Spacer(modifier = Modifier.height(8.dp))
            //设置style样式 Typography排版
            Text(text = "Hello ${message.body}!", style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.height(8.dp))
            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) { //一个面板用来做外围容器
                Text(text = "Hello ${message.body}!",
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.body2)
            }
        }

    }
}

@Preview(name = "Light mode", showBackground = true)
@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewMessageCard() {
    MessageCard(
        message = Message("Android","content")
    )
}