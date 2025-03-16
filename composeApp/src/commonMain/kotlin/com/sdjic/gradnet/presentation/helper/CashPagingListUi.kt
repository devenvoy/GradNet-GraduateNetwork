package com.sdjic.gradnet.presentation.helper


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import gradnet_graduatenetwork.composeapp.generated.resources.Res
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.DotLottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import network.chaintech.sdpcomposemultiplatform.sdp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun <T : Any> CashPagingListUi(
    modifier: Modifier = Modifier,
    data: LazyPagingItems<T>,
    state: LazyListState,
    paddingValues: PaddingValues = PaddingValues(16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    optionalContent: @Composable (T) -> Unit = {},
    bottomPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable LazyItemScope.(T? ,Modifier) -> Unit,
) {

    val composition by rememberLottieComposition {
        LottieCompositionSpec.DotLottie(Res.readBytes("files/loading.lottie"))
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        state = state,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = paddingValues
    ) {


        items(data.itemCount) { index ->
            val item = data[index]
            // Check if this is the last index
            if (index == data.itemCount - 1) {
                // This is the last index, you can add padding or any other logic here
                content(item,Modifier.padding(bottomPadding))
            }else{
                content(item,Modifier)
            }
        }

        data.loadState.apply {
            when {
                refresh is LoadStateNotLoading && data.itemCount < 1 -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No Items",
                                modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                refresh is LoadStateLoading -> {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.size(80.sdp),
                                painter = rememberLottiePainter(
                                    composition = composition,
                                    iterations = Compottie.IterateForever
                                ),
                                contentDescription = "loader"
                            )
                        }
                    }
                }

                append is LoadStateLoading -> {
                    item {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                refresh is LoadStateError -> {
                    item {
                        ErrorView(
                            message = "No Internet Connection",
                            onClickRetry = { data.retry() },
                            modifier = Modifier.fillParentMaxSize()
                        )
                    }
                }

                append is LoadStateError -> {
                    item {
                        ErrorItem(
                            message = "No Internet Connection",
                            onClickRetry = { data.retry() },
                        )
                    }
                }
            }
        }
    }
}

