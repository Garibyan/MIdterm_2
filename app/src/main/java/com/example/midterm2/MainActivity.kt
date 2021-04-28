package com.example.midterm2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import com.wajahatkarim3.easyvalidation.core.view_ktx.creditCardNumber
import com.wajahatkarim3.easyvalidation.core.view_ktx.creditCardNumberWithDashes
import com.wajahatkarim3.easyvalidation.core.view_ktx.greaterThan
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBuyNow.setOnClickListener {
            if (isValid()) {
                edtCardType.text.clear()
                edtName.text.clear()
                edtCardNumber.text.clear()
                edtDateMonth.text.clear()
                edtDateYear.text.clear()
                edtCvvCode.text.clear()
                Toast.makeText(this, "Transaction copleted", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Something is wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun isValid(): Boolean {

        nonEmptyList(
            edtCardType,
            edtName,
            edtCardNumber,
            edtDateMonth,
            edtDateYear,
            edtCvvCode
        ) { view, message ->
            view.error = "This value can't be empty"
        }

        if (edtDateMonth.text.toString().length > 2 ||
            edtDateYear.text.toString().length > 2 ||
            edtDateMonth.greaterThan(12) ||
            edtDateMonth.text.isEmpty() ||
            edtDateYear.text.isEmpty()
        ) {
            return false
        } else {
            val currentDate = LocalDate.now()
            val expirationDate = LocalDate.of(
                (2000 + edtDateYear.text.toString().toInt()),
                edtDateMonth.text.toString().toInt(),
                1 //radgan chvens shemtxvevashi dges mnishvneloba ar aqvs 1 chavwere
            )

            if (expirationDate.isBefore(currentDate)) {
                return false
            }
        }

        if (edtName.text.isEmpty()) {
            return false
        }

        when (edtCardType.text.toString()) {
            "Visa", "MasterCard" -> return (edtCardNumber.text.toString().length == 16 && edtCvvCode.text.toString().length == 4)
            "Amex" -> return (edtCardNumber.text.toString().length == 15 && edtCvvCode.text.toString().length == 3)
            else -> {
                edtCardType.error = "Incorrect card type. Please try again"
                return false
            }
        }
    }
}
