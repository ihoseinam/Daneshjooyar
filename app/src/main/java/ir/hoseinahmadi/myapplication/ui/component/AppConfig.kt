package ir.hoseinahmadi.myapplication.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ir.hoseinahmadi.myapplication.utils.Constants
import ir.hoseinahmadi.myapplication.viewModel.DatStoreViewModel

@Composable
fun AppConfig(
    datStoreViewModel: DatStoreViewModel = hiltViewModel()
) {
    Constants.CHECK_LOGIN = datStoreViewModel.getIsLogin() ?: false
}